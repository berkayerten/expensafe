<template>
  <div>
    <UILibMenu ref="menu" :model="menuItems" :popup="true"/>

    <UILibPopover ref="sharePopover">
      <div class="share-popover-content">
        <div>
          <span class="font-medium block mb-2">Share this expense category</span>
          <UILibInputGroup>
            <UILibInputText :value="shareLink" readonly class="share-link-input"></UILibInputText>
            <UILibInputGroupAddon @click="copyShareLink" class="copy-icon">
              <i :class="copied ? 'pi pi-check' : 'pi pi-copy'"></i>
            </UILibInputGroupAddon>
          </UILibInputGroup>
        </div>
      </div>
    </UILibPopover>
  </div>
</template>

<script>
export default {
  name: 'CategoryCardActions',
  data() {
    return {
      shareLink: 'https://expensafe.site/expense/12323ff26t2g243g423g234gg52hy25XADXAG3',
      copied: false,
      activeCardEvent: null, // To store the event for popover positioning
      menuItems: [
        {
          label: 'Invite collaborators',
          icon: 'pi pi-users',
          command: () => {
            // This now calls a local method
            this.showSharePopover();
          }
        }
      ]
    };
  },
  props: {
    category: {
      type: Object,
      required: false
    }
  },
  methods: {
    /**
     * Public method that the parent component will call.
     * It saves the click event and opens the context menu.
     */
    open(event) {
      this.activeCardEvent = event;
      this.$refs.menu.toggle(event);
    },

    // This logic is now self-contained within this component
    showSharePopover() {
      this.$refs.sharePopover.toggle(this.activeCardEvent);
    },

    copyShareLink() {
      navigator.clipboard.writeText(this.shareLink);
      this.copied = true;
      setTimeout(() => {
        this.copied = false;
      }, 2000);
    }
  }
};
</script>

<style scoped>
.share-popover-content {
  display: flex;
  flex-direction: column;
  gap: 1rem;
  width: 25rem;
}

.share-link-input {
  width: 100%;
}

.copy-icon {
  cursor: pointer;
}
</style>